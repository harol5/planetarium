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
kubectl get pod

#apply resource.
kubectl apply -f #fileName.yml

#Shows all resources created in the cluster. (Does not show ConfigMap and Secret).
kubectl get all

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

kubectl set image delployment/planetarium-deployment client=hrcode95/planetarium:newVersion

eval $(minikube docker-env) # connect to docker inside vm on current terminal windomw.

kubectl get storageclass #show options that kubernetes has to create a persistent volume.

kubectl get pv #persistent volumes

kubectl get all -n ingress-nginx

kubectl get pods -n ingress-nginx

kubectl get services -n ingress-nginx

kubectl get ingress

kubectl edit ingress demo-localhost

kubectl edit service ingress-nginx-controller -n ingress-nginx
