import {
  Box,
  Button,
  Center,
  Flex,
  Heading,
  Text,
  useDisclosure,
  useToast,
} from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import axios from "axios";
import StoreMenuText from "../../../css/theme/component/text/StoreMenuText.jsx";
import StoreMenuCursorBox from "../../../css/theme/component/box/StoreMenuCursorBox.jsx";
import { faCartShopping } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import AddCartModal from "./cart/AddCartModal.jsx";
import ModifyProductModal from "./modify/ModifyProductModal.jsx";
import DeleteProductModal from "./delete/DeleteProductModal.jsx";
import ProductItemList from "./list/ProductItemList.jsx";
import CenterBox from "../../../css/theme/component/box/CenterBox.jsx";

export function StoreList() {
  const [productList, setProductList] = useState([]);
  const [menuTypeSelect, setMenuTypeSelect] = useState("all");
  const [productId, setProductId] = useState(0);
  const [fileName, setFileName] = useState("");
  const [name, setName] = useState("");
  const [price, setPrice] = useState(0);
  const [stock, setStock] = useState(0);
  const [file, setFile] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const toast = useToast();
  const navigate = useNavigate();

  const {
    isOpen: isDelOpen,
    onOpen: onDelOpen,
    onClose: onDelClose,
  } = useDisclosure();

  const {
    isOpen: isModifyOpen,
    onOpen: onModifyOpen,
    onClose: onModifyClose,
  } = useDisclosure();

  const {
    isOpen: isCartOpen,
    onOpen: onCartOpen,
    onClose: onCartClose,
  } = useDisclosure();

  const productListRefresh = () => {
    axios
      .get(`/api/store/product/list/${menuTypeSelect}`)
      .then((res) => {
        setProductList(res.data);
      })
      .catch(() => {})
      .finally(() => {});
  };

  useEffect(() => {
    productListRefresh();
  }, [menuTypeSelect]);

  return (
    <Center>
      <CenterBox mb={10}>
        <Flex>
          <Box w={"50%"}>
            <Flex alignItems={"center"}>
              <Heading>상품 리스트</Heading>
              <Text color={"red"} onClick={() => navigate("cart")}>
                {" "}
                <FontAwesomeIcon
                  icon={faCartShopping}
                  fontSize={"1.2rem"}
                  cursor={"pointer"}
                />
              </Text>
            </Flex>
          </Box>
          <Box w={"50%"} textAlign={"right"}>
            <Button
              onClick={() => navigate("/store/add")}
              colorScheme={"green"}
            >
              상품등록
            </Button>
          </Box>
        </Flex>

        <Flex
          w={"100%"}
          style={{
            textAlign: "center",
          }}
          p={7}
        >
          <StoreMenuCursorBox>
            <StoreMenuText onClick={() => setMenuTypeSelect("all")}>
              전체
            </StoreMenuText>
          </StoreMenuCursorBox>
          <StoreMenuCursorBox>
            <StoreMenuText>Best</StoreMenuText>
          </StoreMenuCursorBox>
          <StoreMenuCursorBox onClick={() => setMenuTypeSelect(1)}>
            <StoreMenuText>세트</StoreMenuText>
          </StoreMenuCursorBox>
          <StoreMenuCursorBox>
            <StoreMenuText onClick={() => setMenuTypeSelect(2)}>
              팝콘
            </StoreMenuText>
          </StoreMenuCursorBox>
          <StoreMenuCursorBox>
            <StoreMenuText onClick={() => setMenuTypeSelect(3)}>
              간식
            </StoreMenuText>
          </StoreMenuCursorBox>
          <StoreMenuCursorBox>
            <StoreMenuText onClick={() => setMenuTypeSelect(4)}>
              드링크
            </StoreMenuText>
          </StoreMenuCursorBox>
        </Flex>

        <Box>
          <hr />
        </Box>

        <ProductItemList
          productList={productList}
          onCartOpen={onCartOpen}
          setProductId={setProductId}
          setFileName={setFileName}
          setName={setName}
          setPrice={setPrice}
          setStock={setStock}
          setQuantity={setQuantity}
          onDelOpen={onDelOpen}
          onModifyOpen={onModifyOpen}
        />

        <DeleteProductModal
          isDelOpen={isDelOpen}
          onDelClose={onDelClose}
          isLoading={isLoading}
          setIsLoading={setIsLoading}
          productId={productId}
          setProductList={setProductList}
          productList={productList}
        />

        <ModifyProductModal
          isModifyOpen={isModifyOpen}
          onModifyOpen={onModifyOpen}
          productId={productId}
          fileName={fileName}
          name={name}
          setName={setName}
          stock={stock}
          setStock={setStock}
          price={price}
          setPrice={setPrice}
          file={file}
          setFile={setFile}
          isLoading={isLoading}
          onModifyClose={onModifyClose}
          productListRefresh={productListRefresh}
        />

        <AddCartModal
          isCartOpen={isCartOpen}
          onCartClose={onCartClose}
          productId={productId}
          fileName={fileName}
          price={price}
          quantity={quantity}
          name={name}
        />
      </CenterBox>
    </Center>
  );
}
