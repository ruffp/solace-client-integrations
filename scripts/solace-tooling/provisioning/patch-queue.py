import http.client
import json
import sys

queueName = sys.argv[1]

import http.client
import json

conn = http.client.HTTPConnection("localhost", 8090)
payload = json.dumps({
    "permission": "consume",
    "egressEnabled": True,
    "ingressEnabled": True
})
headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Basic YWRtaW46YWRtaW4='
}
conn.request("PATCH", "/SEMP/v2/config/msgVpns/default/queues/" + queueName, payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))
