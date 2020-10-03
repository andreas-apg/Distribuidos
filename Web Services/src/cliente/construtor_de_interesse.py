from loggable import Loggable
from helpers import Helpers
from models.interesse import Interesse

class ConstrutorDeInteresse(Loggable):
    """ Classe para construir Interesse do usuario em uma cotacao """   

    def __init__(self, nome_de_usuario):
        super().__init__()
        self.nome_de_usuario:str = nome_de_usuario
        self.interesse: Interesse = Interesse()

    def obter_interesse_do_usuario(self) -> Interesse:
        try:
            self.interesse.nomeDeUsuario = self.nome_de_usuario
            self.interesse.codigoDaAcao = Helpers().obter_codigo_da_acao()
            self.interesse.tipoDaAtualizacao = Helpers().obter_tipo_da_atualizacao()
        except:
            self.log_error(msg= "Erro ao obter interesse do usuario")
            raise

        return self.interesse