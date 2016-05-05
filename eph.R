A = read.csv("/path/to/csv/file", sep=",",header=F)
names(A)<-c("Date","Component”,"Severity","Count")
A$Date<-as.Date(as.character(A$Date), format="%Y%m%d") 
B=subset(A, Date>=as.Date("YYYY-MM-DD”))
ggplot(B,aes(x=Date,y=Count))+geom_line(aes(color=Severity))+facet_wrap(~Component,nrow=10,ncol=5)
ggplot(B,aes(x=Date,y=Count))+geom_line(aes(color=Severity))+facet_wrap(~Component~Severity,nrow=10,ncol=5)
