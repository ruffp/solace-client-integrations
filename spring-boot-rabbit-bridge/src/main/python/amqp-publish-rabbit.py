from amqpstorm import Connection
from amqpstorm import Message

connection = Connection('localhost', 'guest', 'guest', 4672)

channel = connection.channel()

# Message Properties.
properties = {
    'content_type': 'text/plain',
    'headers': {'key': 'value'}
}

# Create the message.
message = Message.create(channel=channel, body='Hello World!', properties=properties)

message.publish(routing_key='TO_SOLACE')