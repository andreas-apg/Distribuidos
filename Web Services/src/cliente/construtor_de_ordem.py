from models.ordem import Ordem
from helpers import Helpers
from loggable import Loggable

class ConstrutorDeOrdem(Loggable):

    def __init__(self, nome_de_usuario):
        super().__init__()
        self.ordem: Ordem = Ordem()
        self.nome_de_usuario:str = nome_de_usuario

    def obter_ordem_do_usuario(self) -> Ordem:
        try:
            self.ordem.nomeDeUsuario = self.nome_de_usuario
            self.ordem.tipoDaOrdem = Helpers().obter_tipo_da_ordem()
            self.ordem.codigoDaAcao = Helpers().obter_codigo_da_acao()
            self.ordem.valor = Helpers().obter_valor()
            self.ordem.quantidade = Helpers().obter_quantidade()
            self.ordem.prazo = Helpers().obter_prazo()
        except:
            self.log_error(msg= "Erro ao obter ordem do usuario")
            raise

        return self.ordem


    