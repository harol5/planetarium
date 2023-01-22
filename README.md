# Deploying in minikube
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

-----------------------------------------------------------------------------------------------------------------------

# Deploying in GKE
* create namespace production

## Postgres
* kubectl --namespace=production apply -f postgres-db
* create tables= kubectl exec -it <podName> -c postgres -- psql -d postgres -U postgres

## Ingress helm
      !!!!now that we're implementing namespaces, might cause some issue, we'll see.
* helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
* helm install my-release ingress-nginx/ingress-nginx
* kubectl create clusterrolebinding cluster-admin-binding \
  --clusterrole cluster-admin \
  --user $(gcloud config get-value account)
* kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.5.1/deploy/static/provider/cloud/deploy.yaml

## Prometheus monitoring
     !!!!now that we're implementing namespaces, might cause some issue, we'll see.
* helm install -f prometheus-grafana-values.yml prometheus-monitoring prometheus-community/kube-prometheus-stack

## Loki stack helm
* helm upgrade --install loki grafana/loki-stack #make persistent, edit file to do so.
* apply loki-pvc.yml
* kubectl edit statefulset.apps/loki

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

## Jenkins helm
* helm install jenkins-planetarium jenkins/jenkins -f jenkins-values.yml
* kubectl exec --namespace default -it svc/jenkins-planetarium -c jenkins -- /bin/cat /run/secrets/additional/chart-admin-password && echo
* kubectl create clusterrolebinding jenkins-deploy \
  --clusterrole=cluster-admin --serviceaccount=default:jenkins-planetarium

    ### Jenkins setup with github
       1. create pipiline
       2. Build Triggers: githud hook
       3. pipeline script from scm > add github repo url > /main > Script path: Jenkinsfile > uncheck Lightweight checkout.
       4. go to the github repo and add wedHook: settings>Webhooks>addWebhook> http://{ip}/jenkins/github-webhook/
       5. create Jenkinsfile
       6. git add, git commit, git push

## Planetarium app
* kubectl --namespace=production apply -f planetarium-app
* after pushing into github, jenkins should build test and deploy the planetarium and ingress service manifest to GKE.


helm upgrade [RELEASE] [CHART] [flags]