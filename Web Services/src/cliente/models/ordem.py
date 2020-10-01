# to use clas as DTO, simplifies jsonification of the class
from dataclasses import dataclass 
from models.enums import *

@dataclass()
class Ordem():
    """ Data Class para ordem de compra ou venda """

    nomeDeUsuario: str
    tipoDaOrdem: str
    codigoDaAcao: str
    valor: float
    quantidade: int
    
    #formato para prazo: "dd-MM-yyyy_HH:mm:ss"
    prazo: str 

    def __init__(self):
        super().__init__()
