# to use clas as DTO, simplifies jsonification of the class
from dataclasses import dataclass 
from models.enums import *

@dataclass()
class Limite():
    """ Data Class para limite de perda ou ganho """

    nomeDeUsuario: str
    tipoDaAtualizacao: str
    tipoDoLimite: str
    codigoDaAcao: str
    valor: float
    
    def __init__(self):
        super().__init__()
