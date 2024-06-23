/* eslint-disable no-undef */
import React, { useCallback, useEffect, useState } from 'react';
import './App.css';
import { Button, Divider, Flex, Form, Input, Modal, Popconfirm, Select, Space, Table, notification } from 'antd';

import axios from "../src/config/axiosConfig";
import Title from 'antd/es/typography/Title';

const App = () => {
  const [products, setProducts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingProduct, setEdittingProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  
  const [form] = Form.useForm();
  const [api, contextHolder] = notification.useNotification();

  const getAllProducts = useCallback(() => {
    axios.get('/api/v1/product')
      .then(response => {
        setProducts(response.data);
        setLoading(false);
      })
      .catch(error => console.log(error));
  }, [setProducts]);

  useEffect(() => {
    getAllProducts();
  }, [getAllProducts]);

  const openSupplierNotification = (operation, productName) => {
    if (operation === 'create'){
      api['success']({
        message: 'Adicionado com sucesso!',
        description: `O produto ${productName} foi adicionado com sucesso!`
      });
    }

    if (operation === 'update'){
      api['success']({
        message: 'Atualizado com sucesso!',
        description: `O produto ${productName} foi atualizado com sucesso!`
      });
    }

    if (operation === 'delete'){
      api['success']({
        message: 'Deletado com sucesso!',
        description: `O produto ${productName} foi deletado com sucesso!`
      });
    }
  };

  const handleOnDelete = (record) => {
  axios.delete(`/api/v1/product/${record.id}`)
    .then(response => {
      if(response.status === 204){
        setProducts(prevProducts => prevProducts.filter(product => product.id !== record.id));
        openSupplierNotification('delete', record.name);
      }
    })
    .catch(errorInfo => console.log('Error: ', errorInfo));
  };

  const handleOnUpdate = (record) => {
    setEdittingProduct(record);
    form.setFieldsValue(record);
    setIsModalOpen(true);
  };

  const handleOnFinish = (values) => {
    if (editingProduct) {
      const edittedProduct = {...values, status: true};
      axios.put(`api/v1/product/${editingProduct.id}`, edittedProduct)
        .then(response => {
          setProducts(prevProducts => prevProducts.map(product =>
            product.id === editingProduct.id ? response.data : product
          ));
          form.resetFields();
          openSupplierNotification('update', response.data.name);
          setIsModalOpen(false);
        })
        .catch(error => console.log('Error: ', error));

    } else {
      axios.post('/api/v1/product', values)
        .then(response => {
          setProducts(prevProducts => [...prevProducts, response.data]);
          openSupplierNotification('create', response.data.name);
          form.resetFields();
        })
        .catch(error => console.log(error));

      setIsModalOpen(false);
    }

  };

  const transformedProducts = products.map(product => {
    return {
      ...product,
      key: product.id
    };
  });

  const showModal = () => {
    setEdittingProduct(null);
    setIsModalOpen(true);
  };

  const formatPrice = (price) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(price);
  };

  const columns = [
    {
      title: 'Nome',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Valor',
      dataIndex: 'price',
      key: 'price',
      width: 200,
      defaultSortOrder: 'ascend',
      sorter: (a, b) => a.price - b.price,
      render: (text, record) => formatPrice(record.price),
    },
    {
      title: 'Ação',
      key: 'action',
      render: (_, record) => (
        <Space size="middle">
          <a onClick={() => handleOnUpdate(record)}>Editar</a>
            <Popconfirm
              title="Excluir produto"
              description="Você tem certeza que deseja excluir produto ?"
              onConfirm={() => handleOnDelete(record)}
              okText="Sim"
              cancelText="Não"
            >
              <Button>Excluir</Button>
            </Popconfirm>
        </Space>
      ),
    },
  ];
  
  return (
    <>
      {contextHolder}
      <div className="app-container">
        {contextHolder}
        <Title>Lista de Produtos</Title>
        <Divider />
        <Flex gap="middle" vertical>
          <Button type="primary" onClick={showModal}>Adicionar novo produto</Button>
          <Table 
            dataSource={products && transformedProducts} 
            columns={columns} 
            showSorterTooltip={{
              target: 'sorter-icon'
            }}
            loading={loading}
          />
        </Flex>
        <Modal
          title={editingProduct ? 'Editar Produto' : 'Adicionar Produto'}
          open={isModalOpen}
          okText={editingProduct ? 'Salvar' : 'Adicionar'}
          okButtonProps={{
            autoFocus: true,
            htmlType: 'submit'
          }}
          cancelText="Cancelar"
          onCancel={() => {
            setIsModalOpen(false);
            form.resetFields();
          }}
          destroyOnClose
          modalRender={(dom) => (
            <Form
              name="form_modal"
              layout="vertical"
              form={form}
              labelCol={{
                span: 4
              }}
              onFinish={(values) => handleOnFinish(values)}
              onFinishFailed={(errorInfo) => console.log('Failed: ', errorInfo)}
            >
              {dom}
            </Form>
          )}
        >
          <Form.Item
            label="Nome"
            name="name"
            rules={[
              {
                required: true,
                message: 'Por favor, insira o nome do produto!'
              }
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Descrição"
            name="description"
            rules={[
              {
                required: true,
                message: 'Por favor, insira a descrição do produto!'
              }
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Valor"
            name="price"
            rules={[
              {
                required: true,
                message: 'Por favor, insira o valor do produto!'
              }
            ]}
          >
            <Input type='number' />
          </Form.Item>
          <Form.Item
            name="available"
            rules={[
              {
                required: true,
                message: 'Por favor, selecione a disponibilidade!'
              }
            ]}
          >
            <Select
              placeholder="Disponibilidade"
              style={{
                width: 120,
              }}
              options={[
                {
                  value: true,
                  label: 'Disponível',
                },
                {
                  value: false,
                  label: 'Indisponível',
                }
              ]}
            />
          </Form.Item>
        </Modal>
      </div>
    </>
  );
}

export default App
