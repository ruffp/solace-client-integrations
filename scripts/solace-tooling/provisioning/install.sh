# Setup Basic auth with Internal database (needed for RabbitMQ bridge
# python3 patch-vpn.py

# create queue for RabbitMQ Bridge and Direct messaging
python3 create-queue.py FROM_RABBIT
python3 create-queue.py TO_RABBIT
python3 create-queue.py TEST_PYTHON

python3 patch-queue.py FROM_RABBIT
python3 patch-queue.py TO_RABBIT
python3 patch-queue.py TEST_PYTHON

# Create queue with subscriptions
python3 create-queue.py queue-football
python3 create-queue.py queue-hockey
python3 create-queue.py queue-basketball
python3 create-queue.py queue-handball
python3 create-queue.py queue-tennis

python3 subscribe-queue.py queue-football news/sport/football
python3 subscribe-queue.py queue-hockey news/sport/hockey
python3 subscribe-queue.py queue-basketball news/sport/basketball
python3 subscribe-queue.py queue-handball news/sport/handball
python3 subscribe-queue.py queue-tennis news/sport/tennis

python3 patch-queue.py queue-football
python3 patch-queue.py queue-hockey
python3 patch-queue.py queue-basketball
python3 patch-queue.py queue-handball
python3 patch-queue.py queue-tennis
