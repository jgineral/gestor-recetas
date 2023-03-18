
provider "aws" {
  region = "eu-south-2"
  skip_region_validation = true
}

data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

resource "aws_security_group" "instance" {
  name = "gestor-recetas"

  ingress {
    from_port = 80
    to_port = 80
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_launch_configuration" "gestor_recetas" {
  image_id = "ami-030f3422cd387984d"
  instance_type = "t3.micro"
  security_groups = [aws_security_group.instance.id]

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_autoscaling_group" "gestor_recetas_asg" {
  launch_configuration = aws_launch_configuration.gestor_recetas.name
  vpc_zone_identifier = data.aws_subnets.default.ids

  target_group_arns = [aws_lb_target_group.gestor_recetas_tg.arn]
  health_check_type = "ELB"

  min_size = 1
  desired_capacity = 2
  max_size = 3

  tag {
    key = "Name"
    value = "gestor-recetas-asg"
    propagate_at_launch = true
  }
}

resource "aws_lb" "gestor_recetas_lb" {
  name = "gestor-recetas"
  load_balancer_type = "application"
  subnets = data.aws_subnets.default.ids
  security_groups = [aws_security_group.gestor_recetas_alb.id]
}

resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.gestor_recetas_lb.arn
  port = 80
  protocol = "HTTP"

  default_action {
    type = "fixed-response"

    fixed_response {
      content_type = "text/plain"
      message_body = "Page Not Found"
      status_code = 404
    }
  }
}

resource "aws_security_group" "gestor_recetas_alb" {
  name = "gestor-recetas-alb"

  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_lb_target_group" "gestor_recetas_tg" {
  name = "gestor-recetas-tg"
  port = 80
  protocol = "HTTP"
  vpc_id = data.aws_vpc.default.id

  health_check {
    path = "/"
    protocol = "HTTP"
    matcher = "200"
    interval = 15
    timeout = 5
    healthy_threshold = 3
    unhealthy_threshold = 3
  }
}

resource "aws_lb_listener_rule" "asg" {
  listener_arn = aws_lb_listener.http.arn
  priority = 100

  condition {
    path_pattern {
      values = ["*"]
    }
  }

  action {
    type = "forward"
    target_group_arn = aws_lb_target_group.gestor_recetas_tg.arn
  }
}

output "spring_alb_dns_name" {
  value = aws_lb.gestor_recetas_lb
  description = "Domain name of the Gestor de Recetas application load balancer"
}