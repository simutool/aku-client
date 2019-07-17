# Rsync Setup
### 1. Instal rsync
````shell
sudo apt install rsync grsync
````
(asks for confirmation y/n)


### 2. Create configuration file
````shell
cat > /etc/rsyncd.conf
````

with following contents:

````
pid file = /var/run/rsyncd.pid
lock file = /var/run/rsync.lock
log file = /var/log/rsync.log
port = 12002
charset = utf–8

[files]
path = /home/destination/
comment = "public rsync share"
use chroot = true
uid = root
gid = root
read only = false

````
Add an empty line in the end - otherwise it might parse the file wrong.

### 3. Run as daemon
````shell
rsync --daemon
````

### 4. Open port in firewall
Install ufw
````
sudo apt-get install ufw
````

Add rule
**Warning:** for this step I needed to run `docker exec` with `--privileged` flag because I didn't have some permissions to manage network (although runnng as root). Error message: "iptables v1.6.0: can't initialize iptables table `filter': Permission denied (you must be root) Perhaps iptables or your kernel needs to be upgraded."

````
sudo ufw  allow 12002
````


# Launching app

Entry point is class App.java, start it as Java aplication.

Configuration data is stored in file config.yaml. Set your **username**, **passwordFile** and **observeDirectory** variables there.

Requires compiler complience level 1.7 and higher (change under Properties > Java Compiler if any problems occur).