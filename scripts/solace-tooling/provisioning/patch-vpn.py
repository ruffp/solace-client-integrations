#
# This is now useless in the latest version of Solace.
#

import http.client

import http.client
import json

conn = http.client.HTTPConnection("localhost", 8090)
payload = json.dumps({
    "authenticationBasicType": "internal"
})
headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Basic YWRtaW46YWRtaW4='
}
conn.request("PATCH", "/SEMP/v2/config/msgVpns/default", payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))
