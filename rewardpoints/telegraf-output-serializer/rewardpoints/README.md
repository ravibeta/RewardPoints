# RewardPoint Points serializer

The RewardPoint Points serializer outputs metrics in the [RewardPoint Accumulation Service format][RewardPoint-format].

It can be used to write to a file using the file output, or for sending metrics to a RewardPoint Server with Enable REST endpoint activated using the standard telegraf HTTP output.
If you're using the HTTP output, this serializer knows how to batch the metrics so you don't end up with an HTTP POST per metric.

[RewardPoint-format]: https://github.com/ravibeta/RewardPoints/blob/main/rewardpoints/spring-rewardpoint-rest-rewardpoint/src/main/java/org/springframework/samples/rewardpoint/model/RewardPoint.java


An example event looks like:
```javascript
[{
    "description": "from telegraf",
    "owner_id": 50,
    "points": 50,
    "rewardpoint_date": "10/22/2020"
}]
```
## Using with the HTTP output

To send this data to a RewardPoint Server with Web Server extension activated, you can use the HTTP output, there are some custom headers that you need to add to manage the Web Server authorization, here's a sample config for an HTTP output:

```toml
[[outputs.http]]
  ## URL is the address to send metrics to
  url = "http://<mid server fqdn or ip address>:8080/rewardpoints/owner/owner_id/"

  ## Timeout for HTTP message
  # timeout = "5s"

  ## HTTP method, one of: "POST" or "PUT"
  method = "POST"

  ## HTTP Basic Auth credentials
  username = 'username'
  password = 'password'

  ## Optional TLS Config
  # tls_ca = "/etc/telegraf/ca.pem"
  # tls_cert = "/etc/telegraf/cert.pem"
  # tls_key = "/etc/telegraf/key.pem"
  ## Use TLS but skip chain & host verification
  # insecure_skip_verify = false

  ## Data format to output.
  ## Each data format has it's own unique set of configuration options, read
  ## more about them here:
  ## https://github.com/influxdata/telegraf/blob/master/docs/DATA_FORMATS_OUTPUT.md
  data_format = "rewardpoints"
  
  ## Additional HTTP headers
  [outputs.http.headers]
  #   # Should be set manually to "application/json" for json data_format
  Content-Type = "application/json"
  Accept = "application/json"
```


## Using with the File output

You can use the file output to output the payload in a file. 
In this case, just add the following section to your telegraf config file

```toml
[[outputs.file]]
  ## Files to write to, "stdout" is a specially handled file.
  files = ["C:/Telegraf/metrics.out"]

  ## Data format to output.
  ## Each data format has its own unique set of configuration options, read
  ## more about them here:
  ## https://github.com/influxdata/telegraf/blob/master/docs/DATA_FORMATS_OUTPUT.md
  data_format = "rewardpoints"
```
