#ifndef CONNECTION_HANDLER__
#define CONNECTION_HANDLER__
                                           
#include <string>
#include <iostream>
#include <boost/asio.hpp>

using boost::asio::ip::tcp;
using namespace std;
class ConnectionHandler {
private:
	const std::string host_;
	const short port_;
	boost::asio::io_service io_service_;   // Provides core I/O functionality
	tcp::socket socket_;
	bool loggedIn;
    bool sentLogout;

public:

    ConnectionHandler(std::string host, short port);
    virtual ~ConnectionHandler();
 
    // Connect to the remote machine
    bool connect();

    bool getLoggedIn();

    void setLoggedIn(bool shouldStop);

    bool getSentLogout();

    void setSentLogout(bool sentLogout);

    void shortToBytes(short num, char *bytesArr,int from);
 
    // Read a fixed number of bytes from the server - blocking.
    // Returns false in case the connection is closed before bytesToRead bytes can be read.
    bool getBytes(char bytes[], unsigned int bytesToRead);
 
	// Send a fixed number of bytes from the client - blocking.
    // Returns false in case the connection is closed before all the data is sent.
    bool sendBytes(const char bytes[], int bytesToWrite);

    // Close down the connection properly.
    void close();



    std::vector<std::string>  stringSplit( std::string &basicString);

    short getOP(std::basic_string<char> &basicString);

    short  bytesToShort(char * bytesarray, int from);



    void insertString(char* bytesarray ,int from ,string &s);

}; //class ConnectionHandler
 
#endif