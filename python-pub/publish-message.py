import argparse

    
def execute_code(env, brand, topic, message):   
    print("Params:" + env + " - " + brand + " - " + topic + " - " + message)


def main():
    parser = argparse.ArgumentParser(description='Publish message in topic.')
    parser.add_argument('env',  help='Environment')
    parser.add_argument('brand',  help='Brand')
    parser.add_argument('topic',  help='Topic')
    parser.add_argument('message',  help='Text message')
    args = parser.parse_args()
    execute_code(args.env, args.brand, args.topic, args.message)                

if __name__ == '__main__':
    main()
