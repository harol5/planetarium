1. This gcloud configuration is called [default]. You can create additional configurations if you work with multiple accounts and/or projects.
   Run `gcloud topic configurations` to learn more.
gcloud auth activate-service-account --key-file service-account.json
gcloud config set project NAME
gcloud config set compute/zone NAME
gcloud container clusters get-credentials ClusterName
gcloud config set compute/region NAME
gcloud help COMMAND
gcloud help config
gcloud topic --help
gcloud cheat-sheet


WARNING:   There are other instances of Google Cloud tools on your system PATH.
Please remove the following to avoid confusion or accidental invocation:

/Applications/Docker.app/Contents/Resources/bin/kubectl