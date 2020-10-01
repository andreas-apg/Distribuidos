import logging

class Loggable:

    def __init__(self):
        self.class_name: str = "[{}]: ".format(type(self).__name__)
        logging.basicConfig(level=logging.INFO)

    def log_error(self, msg:str, exc:Exception=None):
        """ Metodo para imprimir erros no console"""
        logging.error(msg=self.class_name + msg, exc_info=exc)

    def log_info(self, msg:str):
        """ Metodo para imprimir erros no console"""
        logging.info(msg=self.class_name + msg)