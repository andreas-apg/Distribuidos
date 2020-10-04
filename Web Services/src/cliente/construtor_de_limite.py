from loggable import Loggable
from helpers import Helpers
from models.limite import Limite
from models.enums import *

class ConstrutorDeLimite(Loggable):
    """ Classe para construir um Limite de perca ou ganho do usuario em uma acao """   

    def __init__(self, nome_de_usuario):
        super().__init__()
        self.nome_de_usuario:str = nome_de_usuario
        self.limite: Limite = Limite()

    def obter_limite_do_usuario(self) -> Limite:
        try:
            self.limite.nomeDeUsuario = self.nome_de_usuario
            self.limite.codigoDaAcao = Helpers().obter_codigo_da_acao()
            self.limite.tipoDaAtualizacao = Helpers().obter_tipo_da_atualizacao()
            self.limite.tipoDoLimite = Helpers().obter_tipo_do_limite()
            if self.limite.tipoDaAtualizacao == TipoDaAtualizacao.INSERIR.value:
                self.limite.valor = Helpers().obter_valor()
            else:
                self.limite.valor = 0
        except:
            self.log_error(msg= "Erro ao obter limite do usuario")
            raise

        return self.limite