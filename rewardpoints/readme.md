## Prerequisites

You will need to have the latest version of the AWS CLI and maven installed before running the deployment script.  If you need help installing either of these components, please follow the links below:

[Installing the AWS CLI](http://docs.aws.amazon.com/cli/latest/userguide/installing.html)
[Installing Maven](https://maven.apache.org/install.html)

## Deployment

1. Run ```python setup.py -m setup -r <your region>```

## Test
 1. ```curl <your endpoint from output above>/<endpoint>```
 
supported endpoints are /, /pet, /vet, /owner, /rewardpoint

## Clean up

1.  Run ```python setup.py -m cleanup -r <your region>```
  

