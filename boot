#!/usr/bin/env bash
confd -onetime -backend env
python /aws/awslogs-agent-setup.py -n -r ${AWS_REGION} -c /aws/logs.conf
/usr/local/bin/supervisord
tail -f /var/log/awslogs.log
