include "log.thrift"
namespace java com.aug3.logservice.thrift.log.service

service LogService
{
	void log(1: log.LogParam param),
	void logFile(1: string catlog, 2: string level, 3: string machine, 4: list<string> message)
}