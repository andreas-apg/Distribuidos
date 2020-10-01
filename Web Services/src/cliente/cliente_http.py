import requests, sys, json
from models.ordem import Ordem
from dataclasses import asdict
from helpers import Helpers
from loggable import Loggable
from exceptions.server_exception import ServerException

class ClienteHttp(Loggable):
    """Classe responsavel por enviar as mensagens para o servidor de acoes"""

    headers = { 'Content-Type': "application/json" }
    server_url: str = "http://localhost:8080/api"
    server_endpoints: dict = {
        "ordem": "/ordem"
    }
    

    def emitir_ordem(self, ordem:Ordem) -> None:


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


            

    

        