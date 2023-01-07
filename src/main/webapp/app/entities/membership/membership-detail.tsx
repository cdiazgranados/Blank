import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './membership.reducer';

export const MembershipDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const membershipEntity = useAppSelector(state => state.membership.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="membershipDetailsHeading">
          <Translate contentKey="jiveApp.membership.detail.title">Membership</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{membershipEntity.id}</dd>
          <dt>
            <Translate contentKey="jiveApp.membership.user">User</Translate>
          </dt>
          <dd>{membershipEntity.user ? membershipEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="jiveApp.membership.channel">Channel</Translate>
          </dt>
          <dd>{membershipEntity.channel ? membershipEntity.channel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/membership" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/membership/${membershipEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MembershipDetail;
