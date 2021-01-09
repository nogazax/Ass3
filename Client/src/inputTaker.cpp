//
// Created by spl211 on 02/01/2021.
//

using namespace std;
#include "inputTaker.h"
#include "connectionHandler.h"

inputTaker::inputTaker(ConnectionHandler &CH) : ch(CH) {}


void inputTaker::run() {

    while (!ch.getSentLogout()) {
        const short bufsize = 1024;

        char buf[bufsize];

        cin.getline(buf, bufsize);
        string line(buf);
        vector<string> input = ch.stringSplit(line);
        int outputLength;
        short op = ch.getOP(input[0]);
        if (ch.getLoggedIn()&&op==4)
            ch.setSentLogout(true);

        if (op == 1 || op == 2 || op == 3) { //op string 0 string 0, stud\admin reg & login

            outputLength = input[1].size() + input[2].size() + 2 + 2 + 1; //username, pass, opt(*2), \0(*2), \n
            char output[outputLength];
            ch.shortToBytes(op, output, 0); //put op at first 2 bytes
            ch.insertString(output, 2, input[1]); //inserting username and '\0'
            ch.insertString(output, 2 + input[1].size() + 1, input[2]); //inserting password and '\0'
            output[outputLength-1] = '\n';

            ch.sendBytes(output, outputLength);
        } else if (op == 4 || op == 11) // logout or my courses, just send op
        {
            int outputLength = 3;
            char output[outputLength];
            ch.shortToBytes(op, output, 0);
            output[outputLength-1] = '\n';
            ch.sendBytes(output, outputLength);

        } else if (op == 5 || op == 6 || op == 7 || op == 9 || op == 10) { //regcourse, kdamcheck, course-stat, checkisreg, unreg
            int outputLength = 5;
            char output[outputLength];
            ch.shortToBytes(op, output, 0);
            short courseNum = stoi(input[1]);
            ch.shortToBytes(courseNum, output, 2);
            output[outputLength-1] = '\n';
            ch.sendBytes(output, outputLength);
        } else if (op == 8) {
            int outputLength = 2 + input[1].size() + 1 + 1; //opcode + username +/0 +/n
            char output[outputLength];
            ch.shortToBytes(op, output, 0);
            ch.insertString(output, 2, input[1]); //inserting username and '\0'
            output[outputLength-1] = '\n';
            ch.sendBytes(output, outputLength);
        }
    }
}

