import http.client
import json

conn = http.client.HTTPConnection( host="localhost", port=14672 )
payload = json.dumps({
        "auto_delete": False,
        "durable": True,
        "arguments": {}
})
headers = {
    'content-type': 'application/json',
    'Authorization': 'Basic Z3Vlc3Q6Z3Vlc3Q='
}
conn.request("PUT", "/api/queues/%2F/TO_SOLACE", payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))

conn.request("PUT", "/api/queues/%2F/FROM_SOLACE", payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))