#Creates a kubernetes cluster.
minikube start --driver docker
minikube start --driver=hyperkit

#Check the status of cluster.
minikube status

#Enable load balancer resource.
minikube tunnel

#Stop the cluster.
minikube stop

#deletes the cluster.
minikube delete

minikube ssh

minikube dashboard

#-----------------------------------------------------------------------------------------------

#shows pods.
kubectl get <resource>

#apply resource.
kubectl apply -f #fileName.yml

#Shows ConfigMap.
kubectl get configmap

#Shows Secret.
kubectl get secret

#Help.
kubectl --help

#Describes an specific resources.
kubectl describe #component #ComponentName

#Get logs of pod.
kubectl logs #podName #-f

#Get minikube ip.
minikube ip
#Or using
kubectl get node -o wide

#Get all resources in Kubernetes.
kubectl api-resources
kubectl api-resources --namespaced=true #resouces allowed in namespaces.

#Pod terminal
kubectl exec -it podName sh

kubectl delete -f {fileName}

kubectl create secret generic <secret_name> --from-literal <key=value>

kubectl set image delployment/planetarium-deployment client=hrcode95/planetarium:newVersion

eval $(minikube docker-env) # connect to docker inside vm on current terminal windomw.

kubectl get storageclass #show options that kubernetes has to create a persistent volume.

kubectl get all -n ingress-nginx

kubectl get services -n ingress-nginx

kubectl get ingress

kubectl edit ingress demo-localhost

kubectl edit service ingress-nginx-controller -n ingress-nginx

kubectl delete pv <pv_name> --grace-period=0 --force

kubectl delete pod <PODNAME> --grace-period=0 --force --namespace <NAMESPACE>

kubectl patch pv <pv_name> -p '{"metadata": {"finalizers": null}}'

kubectl --namespace default get pods -l "release=planetarium"
kubectl get secret --namespace default planetarium-grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo

kubectl exec -it pod/postgres-deployment-7f86bb6b4b-47fth -c postgres -- psql -d postgres -U postgres

kubectl config set-context $(kubectl config current-context) --namespace=<nameSpace>

kubectl rollout undo deployment/<deploymentName> #rollback to a previous deployment in que case that a new version of the apps fails.