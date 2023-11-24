FROM rabbitmq:3.10.5

# We install some developer's tooling here (not for prod!)
RUN apt update --fix-missing && \
    apt install -y vim && \
    apt install -y nmap && \
    apt install -y netcat && \
    apt install -y telnet && \
    apt install -y iputils-ping && \
    apt install -y procps

# Install some bash aliases
COPY bash_aliases.txt /tmp/.bash_aliases

# Install Rabbit plugins here
RUN rabbitmq-plugins enable rabbitmq_management && \
    rabbitmq-plugins enable rabbitmq_amqp1_0 && \
    rabbitmq-plugins enable rabbitmq_shovel && \
    rabbitmq-plugins enable rabbitmq_shovel_management

# USER rabbitmq

#RUN cp /tmp/.bash_aliases ~ && \
#    echo "\nif [ -f ~/.bash_aliases ]; then \n\t. ~/.bash_aliases\nfi" >> ~/.bashrc

CMD ["rabbitmq-server"]
