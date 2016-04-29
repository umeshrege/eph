# eph
Flume based imap email push to HDFS.

# How to use?
## Update EmailSearcherR.java file
1. Search and replace **"EMAIL_ID"** with your email address
2. Search and replace **"PASSWORD_FOR_EMAIL"** with your email password
3. Search and replace **"FOLDER_NAME"** with your gmail label

**Note1**: This has been tested only with gmail as of this release. And configurations also reflect the same.

**Note2**: Default this will pull emails from given Folder/label for previous day only. If you need emails from more number of days change the value of variable **"t"** with that integer.

## Create jar file
1. Make sure you have downloaded JavaMail API and related packages 
2. Note path for mail.jar file in the JavaMail API
3. Update the MANIFEST.MF file with Class-Path of mail.jar
4. Compile EmailSearcherR.java file and then create .jar file EmailSearcherR.jar

## Update flume.conf
1. Update **"a1.sources.r1.command"** parameter with complete path to EmailSearcherR.jar
2. Update **"a1.sinks.k1.hdfs.path"** parameter with path on your hadoop HDFS where files will be stored

## Start flume agent
/path/to/bin/flume-ng agent -c /path/to/flume/conf -f /path/to/flume.conf -n agent

##You are good to Go!!!##

