# planinha-core

## Separação do Modelo

Nesse branch vão ser separadas as classes de modelo dal do modelo rest.

Ao invés de abusar das anotações Jackson/JSONB e de fazer firulas com os mapeamentos JPA, vão existir classes `@Entities` refletindo o banco e classes `VO` refletindo a api (documentada no Swagger). E classes de mapeamento explicito farão a conversão.

A ideia é que apenas a camada facade chegue a conhecer os dois modelos. A camada DAL conhece apenas os `@Entities` e a camada _Rest_ conhece apenas os `VO`s.

Como os _Filtros_ são conhecidos de ponta a ponta, eles não podem ter classes de modelo neles (nem modelo DAL, nem modelo da API).

Os _Mappers_, por consequência, fazem parte da camada Facade e não precisam existir fora dela.

