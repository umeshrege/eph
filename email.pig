log = LOAD '/path/to/files/hdfs' Using PigStorage(';') AS (dates:int,num:int,datet:chararray,to:chararray,subject:chararray);
step1 = GROUP log BY (dates,to); 
step2 = FOREACH step1 GENERATE FLATTEN(group), COUNT($1) AS alertcount; 
store step2 into '/path/to/op/dir/hdfs' using PigStorage(',');
