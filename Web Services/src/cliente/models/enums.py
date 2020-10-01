from enum import Enum

class TipoDaOrdem(Enum):
     COMPRA = "compra"
     VENDA = "venda"

class TipoDaAtualizacao(Enum):
     INSERIR = "inserir"   
     REMOVER = "remover"

class TipoDoLimite(Enum):
     GANHO = "ganho"
     PERDA = "perda"