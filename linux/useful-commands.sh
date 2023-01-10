echo -n "whatever" | base64 #use this to encode passwords or anything that you want.

uname -a #system info.

chsh -s /bin/bash #change shell.

which #see where commands live.

sudo cp "fileName" "newFile" #copy a file.

sudo rm "fileName" #remove.

df -ah # space of all files systems disk free

du -sh "folderName" #check total size of a directory.

lsblk #hard drive.

lsof #open files.

#---------------------Processes amd daemons-------------------------------------
ps #list whats running on the terminal. Process id (PID)

ps --help simple

ps -u "user" #by user.

ps aux | grep nginx #check cpu uses, use pipe operator/ processes.

pgrep "processName" #returns process id.

kill "PID" # kill process.

kill -l #list of signals to kill a process.

kill -9 #the numbers refers to the list of signal.

pkill -9 "processName"

pstree

top #list running process sorted by cpu usages.

htop

jobs #list of current running or sleep jobs.

bg "jobId" #starts a sleep process in the background.

fg "jobId" #brings process to the foreground.

ping -c 100 google.com & # the "&" will put the process to the background. but will take the terminal.

systemctl list-units #active daemons (units), parsed and mounted to memory

systemctl list-units --all #active and inactive daemons (units).

systemctl list-units -t service # -t means 'type'

systemctl list-unit-files

service "daemonNameName" status #see services running or,
systemctl status "daemonName"

systemctl start "daemonName"

systemctl stop "daemonName" #stops daemon.

systemctl restart "daemonName"

systemctl reload-or-restart "daemonName"

systemctl disable "daemonName" #disable daemon when machine boots.

systemctl enable "daemonName"

systemctl is-active "daemonName"

systemctl is-enabled "daemonName"

journalctl -ex #systemd logs


#-------------------------------------------------------
mount #mounting a new hard drive

env #enviroment variables.

man "anyCommand" #manual.

apropos "topic or keywork" #this will show you command related to that topic.

#----------Networking------------

netstat -tulpn #check open ports TCP/UDP (network sockets?)

netstat -tln

ifconfig #ip info

ip addr show

ipconfig getifaddr en0 #for mac.

ping -c 100 "domain name or ip address" #to make sure things are up.

curl #read website and more stuff?

curl -i "url" #header

curl -v "url" #>request <respond

wget "url"

#----------Managing users---------

whoami #user.

id #user id group id.

sudo adduser "userName" #add user.

sudo su # change to root user.

passwd "userName" #set password.

usermod "userName" --shell "shell path" #usermod will modified user account.

usermod -l "new username" "current username" #change username.

sudo chown 1000:1000 -R ~/jenkins-data #grand permissions

#---------------Package Manager in amazon-------------
sudo yum update
sudo yum install "name"

#----------------Python-------------------
python -m http.server

#-------------editing file-----------------
docker cp script jenkins:/tmp/script.sh

rm -fr {folderName}





spring.datasource.url=jdbc:postgresql://planetarium.cy6kcah5hexg.us-east-1.rds.amazonaws.com:5432/planetarium
spring.datasource.username=postgres
spring.datasource.password=09032022nycHaLi

# this tells the actuator module what metric endpoints we want access too
management.endpoints.web.exposure.include=health,info,prometheus
management.metrics.distribution.percentiles-histogram.http.server.request: true
management.metrics.distribution.percentiles.http.server.requests: 0.5, 0.9, 0.95, 0.99, 0.999

export PGHOST=jdbc:postgresql://planetarium.cy6kcah5hexg.us-east-1.rds.amazonaws.com:5432/planetarium
export PGUSERNAME=postgres
export PGPASSWORD=09032022nycHaLi