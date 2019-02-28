Service/Website monitor
----

![Optional Text](../master/screenshoot.png)

## Feature
* Auto reload
* Let you known your service status in realtime without ping/browse

## Requirements
* Java

## How-to
* Build application and run with Java or Tomcat (Port 8080)
* Open [index.html](index.html) with browser

# Config
Directly modify ```index.html``` to add your service/website.

```
  data: [
	  {
		type: "url",
		interval: 1000,
		data: 'https://genk.vn',
		test: "must be exists"
	  }, 
	  {
		type: "ping",
		interval: 1000,
		data: 'lazylearn.com'
	   },
	   {
		type: "url",
		interval: 1000,
		test: "api",
		data: 'https://lazylearn.com:8080'
	   }
  ]
```

Format
* type: url / ping
* interval: millisecond
* data: IP address / url
* test (only for website): if test string does not exists, the website will consider as ***Offline***