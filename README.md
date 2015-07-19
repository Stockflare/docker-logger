# Logger

Stockflare uses this repository internally to centralize all of our logging from service containers running on our infrastructure.

This container is loosely based upon the container described in [this AWS blog post]() but has had [confd]() added to it, enabling a little bit of flexibility.

It relies upon a few environment variables that can be configured inside the `docker run` command:

* `AWS_REGION`: Defaults to `us-east-1`, set this to the region your running the container in.
* `CLOUDWATCH_LOG_GROUP`: Defaults to `default`, set this to the name of your logging group.
* `LOG_LOCATION`: Defaults to `/var/log/cloudwatch.log`, you shouldn't normally need to configure this- but hey, go crazy.

## How it works

Usage: `docker run -name logger stockflare/logger`

Now link a service container...

`docker run -link logger -P some/service-of-mine logging-executable 2>&1 >(/usr/bin/logger -t service-of-mine -p local6.info -n logger -P 514)`

This command looks a bit full on, but its pretty simple once you get the hang of it.

Whilst our services take advantage of an [wrapping binary]() added to the [stockflare/base]() container, we've laid it out above for you.
It simply pipes $stdout and $stderr from our `logging-executable` which could be puma for example (if you're running a ruby project) to the logger binary. The logger binary is setup with a few flags that enable it to broadcast over TCP/UDP on port 514 to the docker linked hostname 'logger'.

Done, have fun.
