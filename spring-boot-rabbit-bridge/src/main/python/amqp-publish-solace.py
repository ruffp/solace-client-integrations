from amqpstorm import Connection
from amqpstorm import Message

# This is currently not working - probably the lib is not compatible with amqp 1-0-0
connection = Connection('localhost', 'rabbit', 'rabbit', port=5672, virtual_host="default")

channel = connection.channel()

# Message Properties.
properties = {
    'content_type': 'text/plain',
    'headers': {'key': 'value'}
}

# Create the message.
message = Message.create(channel=channel, body='Hello World!', properties=properties)

message.publish(routing_key='TEST_PYTHON')