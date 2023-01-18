#STEPS
1. make sure you're in the right folder
2. start minikube
3. install nginx helm chart, then run <minikube addons enable ingress>
   helm upgrade --install ingress-nginx ingress-nginx \
   --repo https://kubernetes.github.io/ingress-nginx \
   --namespace ingress-nginx --create-namespace
   see https://kubernetes.github.io/ingress-nginx/ 
4. install loki-stack https://github.com/grafana/helm-charts/tree/main/charts/loki-stack
   helm upgrade --install loki grafana/loki-stack \
   --set promtail.enabled=false
5. install kube-prometheus-stack chart https://github.com/prometheus-community/helm-charts/tree/main/charts/kube-prometheus-stack
   then run <helm install -f prometheus-grafana-values.yml prometheus-monitoring prometheus-community/kube-prometheus-stack>  
6. helm install jenkins-planetarium jenkins/jenkins -f jenkins-values.yml
7. apply promtail-config.yml
8. apply planetarium-config.yml
9. planetarium-secret.yml(command line?)
10. apply planetarium-services-monitor.yml
11. apply planetarium.yml
12. apply ingress-service.yml
13. run <minikube tunnel> on different terminal.
14. test the app.

for gke
apply postgres pvc, postgres secret, postgres clusterip, postgres deployment.
create tables= kubectl exec -it <podName> -c postgres -- psql -d postgres -U postgres
#--------------------------------------------------------------------------------------
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm install my-release ingress-nginx/ingress-nginx
kubectl create clusterrolebinding cluster-admin-binding \
--clusterrole cluster-admin \
--user $(gcloud config get-value account)
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.5.1/deploy/static/provider/cloud/deploy.yaml
#---------------------------------------------------------------------------------------------------
apply promtail config
apply planetarium.yml and ingress service.
#------------------------------------------------------------------------------------
helm install -f prometheus-grafana-values.yml prometheus-monitoring prometheus-community/kube-prometheus-stack
apply planetarium service monitor
#------------------------------------------------------------------------------------------------------------
helm upgrade --install loki grafana/loki-stack #make persistent, edit file to do so.
apply loki-pvc.yml
kubectl edit statefulset.apps/loki
#------------------------------------------------------------------------------------------------------------
helm install jenkins-planetarium jenkins/jenkins -f jenkins-values.yml
kubectl exec --namespace default -it svc/jenkins-planetarium -c jenkins -- /bin/cat /run/secrets/additional/chart-admin-password && echo

volumeMounts:
- mountPath: /tmp
name: tmp
- mountPath: /etc/loki
name: config
- mountPath: /data
name: storage
- mountPath: /tmp/loki/
name: loki-storage

volumes:
- emptyDir: {}
name: tmp
- name: config
secret:
defaultMode: 420
secretName: loki
- emptyDir: {}
name: storage
- name: loki-storage
persistentVolumeClaim:
claimName: loki-pvc