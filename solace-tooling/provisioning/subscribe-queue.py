import http.client
import json
import sys

queueName = sys.argv[1]
topicName = sys.argv[2]

conn = http.client.HTTPConnection("localhost", 8090)
payload = json.dumps({
    "msgVpnName": "default",
    "subscriptionTopic": topicName
})
headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Basic YWRtaW46YWRtaW4='
}

print("payload: " + payload)

conn.request("POST", f"/SEMP/v2/config/msgVpns/default/queues/{queueName}/subscriptions", payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))
