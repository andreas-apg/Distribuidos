package br.edu.webserver.javawebserver.services;

import org.springframework.stereotype.Service;

@Service
public class ServicoSSE {
    // Each notification is sent as a block of text terminated by a pair of newlines. 
    // header("Cache-Control: no-cache");
    //header("Content-Type: text/event-stream");
    // The event stream is a simple stream of text data which must be encoded using UTF-8.
    // Messages in the event stream are separated by a pair of newline characters. 
    // A colon as the first character of a line is in essence a comment, and is ignored.
    // Each message consists of one or more lines of text listing the fields for that message. 
    // Each field is represented by the field name, followed by a colon, 
    // followed by the text data for that field's value.
    // Ex: event: userconnect
    // data: {"username": "bobby", "time": "02:33:48"}

}
