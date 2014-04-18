namespace java com.aug3.logservice.thrift.log
namespace php log
struct LogParam
{
	1:i32 component,
	2:i32 action,
	3:i64 uid,
	4:i32 utyp,
	5:string sessionid,
	6:string ip,
	7:string host,
	8:string reqpath,
	9:string params,
	10:string req,
	11:string cookies,
	12:string referer,
	13:string browser,
	14: optional map<string,string> msg
}