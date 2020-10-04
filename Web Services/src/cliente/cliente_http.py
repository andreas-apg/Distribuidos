import requests, sys, json
from models.ordem import Ordem
from models.interesse import Interesse
from models.limite import Limite
from dataclasses import asdict
from helpers import Helpers
from loggable import Loggable
from exceptions.server_exception import ServerException
import threading
import sseclient
import pprint

class ClienteHttp(Loggable,threading.Thread):
    """Classe responsavel por enviar as mensagens para o servidor de acoes"""

    headers = { 'Content-Type': "application/json" }
    server_url: str = "http://localhost:8080/"
    server_endpoints: dict = {
        "notificacao": "events/notificacao",
        "ordem": "api/ordem",
        "interesses": "api/interesses",
        "carteira": "api/carteira",
        "limites": "api/limites"
    }
    
    def __init__(self, username):
        super().__init__()
        threading.Thread.__init__(self, daemon=True)
        self.username = username

    def run(self):
        self.servico_de_notificacao()

    def servico_de_notificacao(self):
        request_url: str = ClienteHttp.server_url + ClienteHttp.server_endpoints['notificacao']
        payload: dict = {'nomeDeUsuario':self.username}
        print ("Iniciando servico de notificacao ")
        
        response = requests.get(request_url, params=payload, stream=True)
        client = sseclient.SSEClient(response)
        for event in client.events():
            pprint.pprint(event.data)
        print ("Encerrando servico de notificacao")
    

    def emitir_ordem(self, ordem:Ordem) -> None:
        """Envia uma ordem de compra e venda ao Servidor por http """

        request_url: str = ClienteHttp.server_url + ClienteHttp.server_endpoints['ordem']

        # tenta enviar a ordem para o servidor por http request
        try:
            # converte o dto python em json
            payload = json.dumps(asdict(ordem))
            
            self.log_info("Enviando ordem: {}".format(payload))
            
            r = requests.post(request_url, data=payload, headers=ClienteHttp.headers)

            if not(r.status_code == 200 or r.status_code==201):
                raise ServerException()
            
            print(self.class_name + "Ordem enviada com sucesso")
        except TypeError as tp:
            msg = "Erro ao converter ordem em json"
            self.log_error(msg, tp)
        except ServerException as ex:
            msg = "Servidor retornou {}: {}".format(r.status_code, r.content)
            self.log_error(msg, ex)
        except :
            msg = "Erro Desconhecido:"
            self.log_error(msg)

    def obter_interesses(self):
        """ Envia um request para obter as acoes de interesse do usuario com as
            respectivas cotacoes """

        request_url: str = ClienteHttp.server_url + ClienteHttp.server_endpoints['interesses']

        try:
            # parametro do get request            
            payload: dict = {'username':self.username}
            
            # envio do request
            self.log_info("Enviando solicitacao de cotacoes: {}".format(payload))
            r = requests.get(request_url, params=payload)

            # gera excessao se a respota nao foi ok
            if not(r.status_code == 200 or r.status_code==201):
                raise ServerException()
            
            print("Cotacoes da lista de interesse:")
            print(r.json)

        except ValueError as tp:
            msg = "Erro ao decodificar resposta em json"
            self.log_error(msg, tp)
        except ServerException as ex:
            msg = "Servidor retornou {}: {}".format(r.status_code, r.content)
            self.log_error(msg, ex)
        except :
            msg = "Erro Desconhecido:"
            self.log_error(msg)


    def enviar_interesse(self, interesse: Interesse):
        """ Envia um novo interesse em cotacao do Cliente ao Servidor por http """
        
        request_url: str = ClienteHttp.server_url + ClienteHttp.server_endpoints['interesses']

        try:
            # converte o dto python em json
            payload = json.dumps(asdict(interesse))
            
            self.log_info("Enviando interesse: {}".format(payload))
            
            r = requests.post(request_url, data=payload, headers=ClienteHttp.headers)

            if not(r.status_code == 200 or r.status_code==201):
                raise ServerException()
            
            print(self.class_name + "Interesse enviado com sucesso")
        except TypeError as tp:
            msg = "Erro ao converter interesse em json"
            self.log_error(msg, tp)
        except ServerException as ex:
            msg = "Servidor retornou {}: {}".format(r.status_code, r.content)
            self.log_error(msg, ex)
        except :
            msg = "Erro Desconhecido:"
            self.log_error(msg)


    def obter_carteira(self):
        """ Envia um request para obter as acoes que usuario possui na carteira """
        request_url: str = ClienteHttp.server_url + ClienteHttp.server_endpoints['carteira']

        try:
            # parametro do get request            
            payload: dict = {'username':self.username}
            
            # envio do request
            self.log_info("Enviando solicitacao de carteira: {}".format(payload))
            r = requests.get(request_url, params=payload)

            # gera excessao se a respota nao foi ok
            if not(r.status_code == 200 or r.status_code==201):
                raise ServerException()
            
            print("Carteira do cliente:")
            print(r.json)

        except ValueError as tp:
            msg = "Erro ao decodificar resposta em json"
            self.log_error(msg, tp)
        except ServerException as ex:
            msg = "Servidor retornou {}: {}".format(r.status_code, r.content)
            self.log_error(msg, ex)
        except :
            msg = "Erro Desconhecido:"
            self.log_error(msg)


    def obter_limites(self):
        """ Envia um request para obter a lista de limite de perca e ganho do usuario """

        request_url: str = ClienteHttp.server_url + ClienteHttp.server_endpoints['limites']

        try:
            # parametro do get request            
            payload: dict = {'username':self.username}
            
            # envio do request
            self.log_info("Enviando solicitacao de limite: {}".format(payload))
            r = requests.get(request_url, params=payload)

            # gera excessao se a respota nao foi ok
            if not(r.status_code == 200 or r.status_code==201):
                raise ServerException()
            
            print("Limites do cliente:")
            print(r.json)

        except ValueError as tp:
            msg = "Erro ao decodificar resposta em json"
            self.log_error(msg, tp)
        except ServerException as ex:
            msg = "Servidor retornou {}: {}".format(r.status_code, r.content)
            self.log_error(msg, ex)
        except :
            msg = "Erro Desconhecido:"
            self.log_error(msg)

    def enviar_limite(self, limite: Limite):
        """ Envia um novo limite de ganho ou perda do cliente ao Servidor por http """
        
        request_url: str = ClienteHttp.server_url + ClienteHttp.server_endpoints['limites']

        try:
            # converte o dto python em json
            payload = json.dumps(asdict(limite))
            
            self.log_info("Enviando limite: {}".format(payload))
            
            r = requests.post(request_url, data=payload, headers=ClienteHttp.headers)

            if not(r.status_code == 200 or r.status_code==201):
                raise ServerException()
            
            print(self.class_name + "Limite enviado com sucesso")
        except TypeError as tp:
            msg = "Erro ao converter limite em json"
            self.log_error(msg, tp)
        except ServerException as ex:
            msg = "Servidor retornou {}: {}".format(r.status_code, r.content)
            self.log_error(msg, ex)
        except :
            msg = "Erro Desconhecido:"
            self.log_error(msg)