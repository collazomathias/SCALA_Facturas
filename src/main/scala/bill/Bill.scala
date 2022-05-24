package bill
import quantityProduct.QuantityProduct

case class Bill (
    id: Int,
    products: List[QuantityProduct]
)
