
#include "../include/inputTaker.h"
#include <iostream>
#include <thread>
#include <string>

using boost::asio::ip::tcp;

using std::cin;
using std::cout;
using std::cerr;
using std::endl;
using std::string;
using namespace std;


int main(int argc, char *argv[]) {

    string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler *connectionHandler = new ConnectionHandler(host, port);
    if (!(connectionHandler->connect())) {
        cerr << "Cannot connect to " << host << ":" << port << endl;
    }
    inputTaker inp(*connectionHandler);
    thread input(&inputTaker::run, &inp);
    while (!connectionHandler->getSentLogout()) {
        string output;
        char bytes[4];
        connectionHandler->getBytes(bytes, 4); //getting err\ack and original op code
        short op = connectionHandler->bytesToShort(bytes, 0);
        short originalOP = connectionHandler->bytesToShort(bytes, 2);
        bool isAck = (op == 12);
        if (isAck&&originalOP==3)
            connectionHandler->setLoggedIn(true);
        if (isAck) {
            char nextByte [1];
            connectionHandler->getBytes(nextByte, 1);
            while (nextByte[0] != '\0') {
                output += nextByte;
                connectionHandler->getBytes(nextByte, 1);
            }

            output = "ACK " + to_string(originalOP) + output +"\n";
        } else {
            output = "ERR " + to_string(originalOP) + "\n";
        }
        cout << output << flush;
        if (originalOP == 4 && op == 12) {
            connectionHandler->setLoggedIn(true);
        }
       
    }
	connectionHandler->~ConnectionHandler();
    return 1;
}

ConnectionHandler::ConnectionHandler(string host, short port) :
        host_(host), port_(port), io_service_(), socket_(io_service_), loggedIn(false), sentLogout(false){}

ConnectionHandler::~ConnectionHandler() {
    close();
}

void ConnectionHandler::setLoggedIn(bool value) {
    loggedIn = value;
}

bool ConnectionHandler::getLoggedIn() {
    return loggedIn;
}

bool ConnectionHandler::connect() {
    cout << "Starting connect to "
         << host_ << ":" << port_ << endl;
    try {
        tcp::endpoint endpoint(boost::asio::ip::address::from_string(host_), port_); // the server endpoint
        boost::system::error_code error;
        socket_.connect(endpoint, error);
        if (error)
            throw boost::system::system_error(error);
    }
    catch (exception &e) {
        cerr << "Connection failed (Error: " << e.what() << ')' << endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::getBytes(char bytes[], unsigned int bytesToRead) {
    size_t tmp = 0;
    boost::system::error_code error;
    try {
        while (!error && bytesToRead > tmp) {
            tmp += socket_.read_some(boost::asio::buffer(bytes + tmp, bytesToRead - tmp), error);
        }
        if (error)
            throw boost::system::system_error(error);
    } catch (exception &e) {
        cerr << "recv failed (Error: " << e.what() << ')' << endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::sendBytes(const char bytes[], int bytesToWrite) {
    int tmp = 0;
    boost::system::error_code error;
    try {
        while (!error && bytesToWrite > tmp) {
            tmp += socket_.write_some(boost::asio::buffer(bytes + tmp, bytesToWrite - tmp), error);
        }
        if (error)
            throw boost::system::system_error(error);
    } catch (exception &e) {
        cerr << "recv failed (Error: " << e.what() << ')' << endl;
        return false;
    }
    return true;
}



// Close down the connection properly.
void ConnectionHandler::close() {
    try {
        socket_.close();
    } catch (...) {
        cout << "closing failed: connection already closed" << endl;
    }
}

void ConnectionHandler::shortToBytes(short num, char *bytesArr, int from) {
    bytesArr[from] = ((num >> 8) & 0xFF);
    bytesArr[from + 1] = (num & 0xFF);
}

void ConnectionHandler::insertString(char *bytesarray, int from, string &s) {
    for (unsigned int i = 0; i < s.size(); ++i) {
        bytesarray[i + from] = (char) s[i];
    }
    bytesarray[from + s.size()] = '\0';
}


vector<string> ConnectionHandler::stringSplit(string &s) {
    // parsing the user's message to vector of strings
    string delimiter = " ";
    vector<string> ans;
    size_t pos = 0;
    string token;
    while ((pos = s.find(delimiter)) != std::string::npos) {
        token = s.substr(0, pos);
        ans.push_back(token);
        s.erase(0, pos + delimiter.length());
    }
    ans.push_back(s);
    return ans;
}

short ConnectionHandler::getOP(basic_string<char> &basicString) {
    if (basicString == "ADMINREG") return 1;
    if (basicString == "STUDENTREG") return 2;
    if (basicString == "LOGIN") return 3;
    if (basicString == "LOGOUT") return 4;
    if (basicString == "COURSEREG") return 5;
    if (basicString == "KDAMCHECK") return 6;
    if (basicString == "COURSESTAT") return 7;
    if (basicString == "STUDENTSTAT") return 8;
    if (basicString == "ISREGISTERED") return 9;
    if (basicString == "UNREGISTER") return 10;
    if (basicString == "MYCOURSES") return 11;

    return 0;
}

short ConnectionHandler::bytesToShort(char *bytesarray, int from) {

    short result = (short) ((bytesarray[from] & 0xff) << 8);
    result += (short) (bytesarray[from + 1] & 0xff);
    return result;

}

bool ConnectionHandler::getSentLogout() {
    return sentLogout;
}

void ConnectionHandler::setSentLogout(bool sentLogout) {
    ConnectionHandler::sentLogout = sentLogout;
}







