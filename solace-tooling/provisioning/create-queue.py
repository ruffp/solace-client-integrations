import http.client
import json
import sys

queueName = sys.argv[1]

conn = http.client.HTTPConnection("localhost", 8090)
payload = json.dumps({
  "queueName": queueName,
  "egressEnabled": False,
  "ingressEnabled": False
})
headers = {
  'Content-Type': 'application/json',
  'Authorization': 'Basic YWRtaW46YWRtaW4='
}
conn.request("POST", "/SEMP/v2/config/msgVpns/default/queues", payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))