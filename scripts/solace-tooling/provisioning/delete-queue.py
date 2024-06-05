import http.client
import json
import sys

queueName = str(sys.argv[1])

conn = http.client.HTTPConnection("localhost", 8090)
headers = {
  'Content-Type': 'application/json',
  'Authorization': 'Basic YWRtaW46YWRtaW4='
}
conn.request("DELETE", f"/SEMP/v2/config/msgVpns/default/queues/{queueName}", headers=headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))