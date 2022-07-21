import http.client
import json

conn = http.client.HTTPConnection("localhost", 14672)
payload = json.dumps({
        "auto_delete": False,
        "durable": True,
        "arguments": {}
})
headers = {
    'content-type': 'application/json',
    'Authorization': 'Basic Z3Vlc3Q6Z3Vlc3Q='
}
conn.request("PUT", "/api/parameters/queues/%2F/", payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))


curl --location --request PUT 'http://localhost:15671/api/queues/%2F/TEST_QUEUE' \
                              --header 'Content-Type: application/json' \
                                       --header 'Authorization: Basic Z3Vlc3Q6Z3Vlc3Q=' \
                                                --data-raw ''
