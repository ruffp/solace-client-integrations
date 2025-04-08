import http.client
import json

conn = http.client.HTTPConnection("localhost", 14672)
payload = json.dumps({
    "component": "shovel",
    "value": {
        "src-protocol": "amqp10",
        "src-uri": "amqp://solace-host:5672",
        "src-address": "TO_RABBIT",
        "src-prefetch-count": 1,
        "src-delete-after": "never",
        "dest-protocol": "amqp091",
        "dest-uri": "amqp://guest:guest@localhost:5672/%2F",
        "dest-queue": "FROM_SOLACE",
        "dest-add-forward-headers": True,
        "ack-mode": "on-confirm"
    }
})
headers = {
    'content-type': 'application/json',
    'Authorization': 'Basic Z3Vlc3Q6Z3Vlc3Q='
}
conn.request("PUT", "/api/parameters/shovel/%2F/solace-to-rabbit", payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))


