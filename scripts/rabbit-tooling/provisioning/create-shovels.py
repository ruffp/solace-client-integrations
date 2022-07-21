import http.client
import json

conn = http.client.HTTPConnection("localhost", 14672)
payload = json.dumps({
    "component": "shovel",
    "value": {
        "src-uri": "amqp://rabbit:rabbit@solace-host:5672",
        "src-address": "TO_RABBIT",
        "src-protocol": "amqp100",
        "src-prefetch-count": 1,
        "src-delete-after": "never",
        "dest-protocol": "amqp091",
        "dest-uri": "amqp://guest:guest@localhost:5672",
        "dest-add-forward-headers": True,
        "dest-queue": "FROM_SOLACE",
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
