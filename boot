#!/usr/bin/env bash
SEP="==================="
confd -onetime -backend env
echo "Wrote AWS Logs..."
echo $SEP
cat /aws/logs.conf
echo $SEP
echo "Wrote SupervisorD Logs..."
echo $SEP
cat /usr/local/etc/supervisord.conf
echo $SEP
python /aws/awslogs-agent-setup.py -n -r ${AWS_REGION} -c /aws/logs.conf
/usr/local/bin/supervisord -c /usr/local/etc/supervisord.conf
