//
// Created by spl211 on 02/01/2021.
//

#ifndef INPUTTAKER_H__
#define INPUTTAKER_H__
#include "connectionHandler.h"

class inputTaker {
private:
    ConnectionHandler& ch;
public:
    inputTaker(ConnectionHandler& CH);
    void run();


};


#endif //SPL_ASS_3_INPUTTAKER_H
