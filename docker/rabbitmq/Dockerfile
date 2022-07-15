FROM rabbitmq:3.10.5

RUN apt update && \
    apt install -y vim && \
    apt install -y nmap && \
    apt install -y netcat && \
    apt install -y telnet && \
    apt install -y iputils-ping && \
    apt install -y procps

# We install Rabbit plugins here
RUN rabbitmq-plugins enable rabbitmq_management
RUN rabbitmq-plugins enable rabbitmq_amqp1_0
RUN rabbitmq-plugins enable rabbitmq_shovel
RUN rabbitmq-plugins enable rabbitmq_shovel_management

CMD ["rabbitmq-server"]
