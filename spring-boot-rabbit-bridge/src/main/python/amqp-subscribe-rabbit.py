from amqpstorm import Connection

def on_message(message):
    """This function is called on message received.

    :param message: Delivered message.
    :return:
    """
    print("Message:", message.body)

    # Acknowledge that we handled the message without any issues.
    message.ack()

    # Reject the message.
    # message.reject()

    # Reject the message, and put it back in the queue.
    # message.reject(requeue=True)

# initiate connection
connection = Connection('localhost', 'guest', 'guest', 4672)
channel = connection.channel()

# start subscribing
channel.basic.consume(callback=on_message, queue='FROM_SOLACE', no_ack=False)
channel.start_consuming(to_tuple=False)