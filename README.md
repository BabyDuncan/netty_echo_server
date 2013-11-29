netty_echo_server
=================

an echo server implemented by netty , use jurlmapping (https://github.com/BabyDuncan/jurlMapping) for restful urls analyse .

performance test :

	[root@zw_68_118 ~]# ab -n 10000 -c 100 http://10.16.1.48:8085/foobar/aaaaa
	This is ApacheBench, Version 2.0.40-dev <$Revision: 1.146 $> apache-2.0
	Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
	Copyright 2006 The Apache Software Foundation, http://www.apache.org/
	
	Benchmarking 10.16.1.48 (be patient)
	Completed 1000 requests
	Completed 2000 requests
	Completed 3000 requests
	Completed 4000 requests
	Completed 5000 requests
	Completed 6000 requests
	Completed 7000 requests
	Completed 8000 requests
	Completed 9000 requests
	Finished 10000 requests
	
	
	Server Software:        
	Server Hostname:        10.16.1.48
	Server Port:            8085
	
	Document Path:          /foobar/aaaaa
	Document Length:        6 bytes
	
	Concurrency Level:      100
	Time taken for tests:   1.349965 seconds
	Complete requests:      10000
	Failed requests:        0
	Write errors:           0
	Total transferred:      700210 bytes
	HTML transferred:       60018 bytes
	Requests per second:    7407.60 [#/sec] (mean)
	Time per request:       13.500 [ms] (mean)
	Time per request:       0.135 [ms] (mean, across all concurrent requests)
	Transfer rate:          505.94 [Kbytes/sec] received
	
	Connection Times (ms)
	              min  mean[+/-sd] median   max
	Connect:        0    1   1.9      1      15
	Processing:     1   11   7.0     10      42
	Waiting:        0    7   6.4      6      39
	Total:          1   12   7.2     12      44
	
	Percentage of the requests served within a certain time (ms)
	  50%     12
	  66%     15
	  75%     17
	  80%     19
	  90%     23
	  95%     26
	  98%     31
	  99%     34
	 100%     44 (longest request)
